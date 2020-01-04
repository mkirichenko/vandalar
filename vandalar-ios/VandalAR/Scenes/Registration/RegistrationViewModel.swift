import Foundation
import Combine
import SwiftUI

final class RegistrationViewModel: ObservableObject {
	typealias Context = VandalAPIServiceType // & other contexts...
	
	private let context: Context
	private let registerSubject = PassthroughSubject<String, Never>()
	
	@Binding private var isRegistrationComplete: Bool
	
	@Published var isShowingAlert: Bool = false
	@Published private(set) var alertMessage: String? = nil
	
	private var disposeBag = Set<AnyCancellable>()
	
	init(context: Context, isRegistrationComplete: Binding<Bool>) {
		self.context = context
		_isRegistrationComplete = isRegistrationComplete
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
			.sink { [unowned self] _ in
				self.isRegistrationComplete = true
			}
			.store(in: &disposeBag)
	}
	
	func register(name: String) {
		registerSubject.send(name)
	}
}
