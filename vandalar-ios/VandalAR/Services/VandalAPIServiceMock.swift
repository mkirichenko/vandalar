import Foundation
import Combine

private struct StubError: Error {}

final class VandalAPIServiceMock: VandalAPIServiceType {
	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error> {
		Just(RegistrationResponse(publicId: UUID().uuidString))
			.mapError { _ in StubError() }
			.eraseToAnyPublisher()
	}
	
	func fetchNotes(privateId: UUID) -> AnyPublisher<[Note], Error> {
		Just([]).mapError { _ in StubError() }.eraseToAnyPublisher()
	}
	
	func fetchNote(privateId: UUID, id: Int) -> AnyPublisher<Note, Error> {
		Just(Note(id: 0, content: "kek", lat: 10.0, lon: 10.0, height: 10.0, created: Date()))
			.mapError { _ in StubError() }
			.eraseToAnyPublisher()
	}
	
	func addNote(privateId: UUID, content: String, lat: Double, lon: Double, height: Double) -> AnyPublisher<AddNoteResponse, Error> {
		Just(AddNoteResponse(id: 0)).mapError { _ in StubError() }.eraseToAnyPublisher()
	}
}