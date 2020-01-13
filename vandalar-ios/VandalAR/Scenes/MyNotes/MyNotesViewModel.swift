import Foundation
import Combine

final class MyNotesViewModel: ObservableObject {
	typealias Context = VandalAPIServiceType // & other contexts...
	
	private let context: Context
	private let privateId: UUID
	
	@Published var notes: [Note] = []
	@Published var isShowingAlert: Bool = false
	@Published private(set) var alertMessage: String? = nil
	
	private let fetchNotesSubject = PassthroughSubject<Void, Never>()
	
	private var disposeBag = Set<AnyCancellable>()
	
	init(context: Context, privateId: UUID) {
		self.context = context
		self.privateId = privateId
		configureBindings()
	}
	
	private func configureBindings() {
		fetchNotesSubject.flatMap { [unowned self] _ -> AnyPublisher<[Note], Never> in
			self.context.fetchNotes(privateId: self.privateId)
				.handleEvents(receiveCompletion: { completion in
					guard case let .failure(error) = completion else { return }
					self.alertMessage = error.localizedDescription
					self.isShowingAlert = true
				})
				.catch { _ in Empty() }
				.eraseToAnyPublisher()
		}
		.assign(to: \.notes, on: self)
		.store(in: &disposeBag)
	}
	
	func fetchNotes() {
		fetchNotesSubject.send()
	}
}
