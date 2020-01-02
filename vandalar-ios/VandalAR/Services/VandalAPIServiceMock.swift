import Foundation
import Combine

private struct StubError: Error {}

final class VandalAPIServiceMock: VandalAPIServiceType {

	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error> {
		Just(RegistrationResponse(publicId: UUID().uuidString))
			.mapError { _ in StubError() }
			.eraseToAnyPublisher()
	}
}
