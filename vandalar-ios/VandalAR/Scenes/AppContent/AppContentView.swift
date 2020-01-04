import SwiftUI

struct AppContentView: View {
	typealias Context = VandalAPIServiceType
	@State private var isRegistrationComplete: Bool
	private let context: Context
	
	init(isRegistrationComplete: Bool, context: Context) {
		_isRegistrationComplete = State(initialValue: isRegistrationComplete)
		self.context = context
	}
	
	var body: some View {
		Group {
			if isRegistrationComplete {
				// TODO: Show the main AR view
				Text("Main AR View")
			} else {
				RegistrationView(viewModel: RegistrationViewModel(context: context, isRegistrationComplete: $isRegistrationComplete))
			}
		}
	}
}
