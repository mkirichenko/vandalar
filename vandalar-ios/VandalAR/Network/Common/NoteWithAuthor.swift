import Foundation

struct NoteWithAuthor: Codable {
	let id: Int
	let content: String
	let userId: String
	let userName: String
	let lat: Double
	let lon: Double
	let height: Double
	let created: Date
}
