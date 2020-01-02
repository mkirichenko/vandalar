import Foundation
import Combine

protocol VandalAPIServiceType {
	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error>
}
