import Foundation

struct Note: Codable {
	let id: Int
	let content: String
	let lat: Double
	let lon: Double
	let height: Double
	let created: Date
}
