import Foundation
import Combine

enum VandalAPIServiceError: Error {
	case invalidDate
}

protocol VandalAPIServiceType {
	func register(name: String, privateId: UUID) -> AnyPublisher<RegistrationResponse, Error>
	func fetchNotes(privateId: UUID) -> AnyPublisher<[Note], Error>
	func fetchNote(privateId: UUID, id: Int) -> AnyPublisher<Note, Error>
	func addNote(privateId: UUID, content: String, lat: Double, lon: Double, height: Double) -> AnyPublisher<AddNoteResponse, Error>
}
