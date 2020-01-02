import UIKit
import SwiftUI
import Moya

@UIApplicationMain
final class AppDelegate: UIResponder, UIApplicationDelegate {
	var window: UIWindow?
	var context: VandalAPIServiceType!
	
	func application(_ application: UIApplication,
		didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

		
		context = VandalAPIService(apiProvider: MoyaProvider())
		let initialView: AnyView
		if let privateIDString = UserDefaults.standard.string(forKey: UserDefaults.privateIDKey),
			let privateID = UUID(uuidString: privateIDString) {
			
			print("TODO: go on...")
			initialView = AnyView(Text(privateIDString))
		} else {
			let viewModel = RegistrationViewModel(context: context)
			initialView = AnyView(RegistrationView(viewModel: viewModel))
		}
		
		let window = UIWindow(frame: UIScreen.main.bounds)
		window.rootViewController = UIHostingController(rootView: initialView)
		self.window = window
		window.makeKeyAndVisible()
		return true
	}
}

