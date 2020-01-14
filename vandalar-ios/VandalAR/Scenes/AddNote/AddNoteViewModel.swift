import Combine
import SwiftUI
import CoreLocation

final class AddNoteViewModel: ObservableObject {
	typealias Context = VandalAPIServiceType
	
	private let context: Context
	private let locationManager = CLLocationManager()
	private let addSubject = PassthroughSubject<String, Never>()
	
	@Binding private var showSelf: Bool
	
	@Published var isShowingAlert: Bool = false
	@Published private(set) var alertMessage: String? = nil
	
	private var disposeBag = Set<AnyCancellable>()
	
	init(context: Context, showSelf: Binding<Bool>) {
		self.context = context
		_showSelf = showSelf
		configureBindings()
	}
	
	private func configureBindings() {
		addSubject.throttle(for: 3, scheduler: RunLoop.main, latest: false)
			.flatMap { [unowned self] note -> AnyPublisher<AddNoteResponse, Never> in
				let privateId = UUID(uuidString: UserDefaults.standard.string(forKey: UserDefaults.privateIDKey)!)!
				guard let location = self.locationManager.location else { return Empty().eraseToAnyPublisher() }
				return self.context.addNote(
						privateId: privateId,
						content: note,
						lat: location.coordinate.latitude,
						lon: location.coordinate.longitude,
						height: location.altitude
					)
					.handleEvents(receiveCompletion: { completion in
						guard case let .failure(error) = completion else { return }
						self.alertMessage = error.localizedDescription
						self.isShowingAlert = true
				})
				.catch { _ in Empty() }
				.eraseToAnyPublisher()
		}
		.sink { [unowned self] _ in
			self.showSelf = false
		}
		.store(in: &disposeBag)
	}
	
	func add(_ note: String) {
		addSubject.send(note)
	}
}
