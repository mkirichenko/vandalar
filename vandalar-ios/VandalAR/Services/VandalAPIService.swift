import Foundation
import Combine
import Moya

final class VandalAPIService: VandalAPIServiceType {
	private let apiProvider: MoyaProvider<VandalAPI>
	
	init(apiProvider: MoyaProvider<VandalAPI>) {
		self.apiProvider = apiProvider
	}
	
	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error> {
		let registrationRequest = RegistrationRequest(privateId: privateId, name: name)
		return request(target: .register(registrationRequest), responseType: RegistrationResponse.self)
	}
}

private extension VandalAPIService {
	func request<E: Decodable>(target: VandalAPI, responseType: E.Type) -> AnyPublisher<E, Error> {
		apiProvider.requestPublisher(target)
			.filterSuccessfulStatusCodes()
			.map(E.self)
			.mapError { $0 }
			.eraseToAnyPublisher()
	}
}
