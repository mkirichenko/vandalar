import Foundation
import Moya

enum VandalAPI {
	case register(RegistrationRequest)
	case fetchNotes(privateID: String)
	case fetchNote(privateID: String, id: Int)
	case addNote(privateID: String, AddNoteRequest)
}


// MARK: - TargetType
extension VandalAPI: TargetType {
	var baseURL: URL {
		#if targetEnvironment(simulator)
		return URL(string: "localhost:8880/api/v1")!
		#else
		return URL(string: "http://192.168.1.41:8880/api/v1")!
		#endif
	}
	
	var path: String {
		switch self {
		case .register:
			return "/auth/register"
		case .fetchNotes, .addNote:
			return "/note"
		case .fetchNote(_, let id):
			return "/note/\(id)"
		}
	}
	
	var method: Moya.Method {
		switch self {
		case .register, .addNote:
			return .post
		case .fetchNotes, .fetchNote:
			return .get
		}
	}
	
	var task: Task {
		switch self {
		case .register(let request):
			return .requestJSONEncodable(request)
		case .addNote(_, let request):
			return .requestJSONEncodable(request)
		case .fetchNotes, .fetchNote:
			return .requestPlain
		}
	}
	
	var sampleData: Data {
		switch self {
		case .register:
			let response = RegistrationResponse(publicId: "075131f3-e5d2-423d-8103-afc043579cf1")
			return (try? JSONEncoder().encode(response)) ?? Data()
		default:
			return Data()
		}
	}
	
	var headers: [String : String]? {
		switch self {
			case .fetchNotes(let privateID),
				 .fetchNote(let privateID, _),
				 .addNote(let privateID, _):
			return ["X_USER_ID" : privateID]
		default:
			return nil
		}
	}
}
