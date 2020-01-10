import Foundation
import Combine
import Moya

final class VandalAPIService: VandalAPIServiceType {
	private let apiProvider: MoyaProvider<VandalAPI>
	
	init(apiProvider: MoyaProvider<VandalAPI>) {
		self.apiProvider = apiProvider
	}
	
	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error> {
		let registrationRequest = RegistrationRequest(privateId: privateId.serverString, name: name)
		return request(target: .register(registrationRequest), responseType: RegistrationResponse.self)
	}
	
	func fetchNotes(privateId: UUID) -> AnyPublisher<[Note], Error> {
		let privateIdString = privateId.serverString
		let iso8601Decoder = JSONDecoder()
		iso8601Decoder.dateDecodingStrategy = .iso8601
		
		return request(
			target: .fetchNotes(privateID: privateIdString),
			responseType: [Note].self,
			jsonDecoder: iso8601Decoder
		)
	}
	
	func fetchNote(privateId: UUID, id: Int) -> AnyPublisher<Note, Error> {
		let privateIdString = privateId.serverString
		let iso8601Decoder = JSONDecoder()
		iso8601Decoder.dateDecodingStrategy = .iso8601
		
		return request(
			target: .fetchNote(privateID: privateIdString, id: id),
			responseType: Note.self,
			jsonDecoder: iso8601Decoder
		)
	}
	
	func addNote(privateId: UUID, content: String, lat: Double, lon: Double, height: Double) -> AnyPublisher<AddNoteResponse, Error> {
		let privateIdString = privateId.serverString
		let addNoteRequest = AddNoteRequest(content: content, lat: lat, lon: lon, height: height)
		return request(target: .addNote(privateID: privateIdString, addNoteRequest), responseType: AddNoteResponse.self)
	}
}

private extension VandalAPIService {
	func request<E: Decodable>(target: VandalAPI, responseType: E.Type, jsonDecoder: JSONDecoder = JSONDecoder()) -> AnyPublisher<E, Error> {
		apiProvider.requestPublisher(target)
			.filterSuccessfulStatusCodes()
			.map(E.self)
			.mapError { $0 }
			.eraseToAnyPublisher()
	}
}
