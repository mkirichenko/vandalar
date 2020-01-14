import Foundation
import Moya

enum VandalAPI {
	case register(RegistrationRequest)
	case fetchNotes(privateID: String)
	case fetchNote(privateID: String, id: Int)
	case addNote(privateID: String, AddNoteRequest)
	case searchNotes(privateID: String, lat: Double, lon: Double, height: Double, radius: Double)
}


// MARK: - TargetType
extension VandalAPI: TargetType {
	var baseURL: URL {
		#if targetEnvironment(simulator)
		return URL(string: "http://127.0.0.1:8880/api/v1")!
		#else
		return URL(string: "http://172.20.10.2:8880/api/v1")!
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
		case .searchNotes:
			return "/note-search"
		}
	}
	
	var method: Moya.Method {
		switch self {
		case .register, .addNote:
			return .post
			case .fetchNotes, .fetchNote, .searchNotes:
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
		case let .searchNotes(_, lat, lon, height, radius):
			return .requestParameters(parameters: ["lat": lat, "lon": lon, "height": height, "radius": radius],
									  encoding: URLEncoding.queryString)
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
				 .addNote(let privateID, _),
				 .searchNotes(let privateID, _, _, _, _):
			return ["X_USER_ID" : privateID]
		default:
			return nil
		}
	}
}
