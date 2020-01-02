import Foundation
import Combine

final class RegistrationViewModel: ObservableObject {
	typealias Context = VandalAPIServiceType // & other contexts...
	
	private let context: Context
	private let registerSubject = PassthroughSubject<String, Never>()
	
	@Published var isShowingAlert: Bool = false
	@Published private(set) var alertMessage: String? = nil
	@Published var shouldNavigateFurther: Bool = false
	@Published private(set) var publicId: String? = nil
	
	private var disposeBag = Set<AnyCancellable>()
	
	init(context: Context) {
		self.context = context
		configureBindings()
	}
	
	private func configureBindings() {
		registerSubject
			.removeDuplicates()
			.throttle(for: 3, scheduler: RunLoop.main, latest: false)
			.map { ($0, UUID()) }
			.handleEvents(receiveOutput: { name, privateId in
				UserDefaults.standard.set(privateId.uuidString, forKey: UserDefaults.privateIDKey)
			})
			.flatMap { [unowned self] name, privateId -> AnyPublisher<RegistrationResponse, Never> in
				self.context.register(name: name, privateId: privateId)
					.handleEvents(receiveCompletion: { completion in
						guard case let .failure(error) = completion else { return }
						UserDefaults.standard.removeObject(forKey: UserDefaults.privateIDKey)
						self.alertMessage = error.localizedDescription
						self.isShowingAlert = true
					})
					.catch { _ in Empty() }
					.eraseToAnyPublisher()
			}
			.sink { [unowned self] registrationResponse in
				self.publicId = registrationResponse.publicId
				self.shouldNavigateFurther = true
			}
			.store(in: &disposeBag)
	}
	
	func register(name: String) {
		registerSubject.send(name)
	}
}
