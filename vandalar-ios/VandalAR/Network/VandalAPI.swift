import Foundation
import Moya

enum VandalAPI {
	case register(RegistrationRequest)
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
		}
	}
	
	var method: Moya.Method {
		switch self {
		case .register:
			return .post
		}
	}
	
	var task: Task {
		switch self {
		case .register(let request):
			return .requestJSONEncodable(request)
		}
	}
	
	var sampleData: Data {
		switch self {
		case .register:
			let response = RegistrationResponse(publicId: "075131f3-e5d2-423d-8103-afc043579cf1")
			return (try? JSONEncoder().encode(response)) ?? Data()
		}
	}
	
	var headers: [String : String]? { nil }
}
