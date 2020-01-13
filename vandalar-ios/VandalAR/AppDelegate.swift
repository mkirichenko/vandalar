import UIKit
import SwiftUI
import Moya

@UIApplicationMain
final class AppDelegate: UIResponder, UIApplicationDelegate {
	var window: UIWindow?
	var context: VandalAPIServiceType!
	
	func application(_ application: UIApplication,
		didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

		
//		context = VandalAPIService(apiProvider: MoyaProvider())
		context = VandalAPIServiceMock()
		let contentView: AppContentView
		if let privateIDString = UserDefaults.standard.string(forKey: UserDefaults.privateIDKey),
			let privateID = UUID(uuidString: privateIDString) {
			
			contentView = AppContentView(isRegistrationComplete: true, context: context, privateId: privateID)
		} else {
			contentView = AppContentView(isRegistrationComplete: false, context: context, privateId: nil)
		}
		
		let window = UIWindow(frame: UIScreen.main.bounds)
		window.rootViewController = UIHostingController(rootView: contentView)
		self.window = window
		window.makeKeyAndVisible()
		return true
	}
}

