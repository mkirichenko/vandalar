import Foundation

struct RegistrationRequest: Encodable {
	let privateId: UUID
	let name: String
}

extension RegistrationRequest {
	enum CodingKeys: String, CodingKey {
		case privateId
		case name
	}
	
	func encode(to encoder: Encoder) throws {
		var container = encoder.container(keyedBy: CodingKeys.self)
		try container.encode(privateId.uuidString.lowercased(), forKey: .privateId)
		try container.encode(name, forKey: .name)
	}
}
