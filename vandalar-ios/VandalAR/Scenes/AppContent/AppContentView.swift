
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
                ARKitView().frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
                .edgesIgnoringSafeArea(.all)
			} else {
				RegistrationView(viewModel: RegistrationViewModel(context: context, isRegistrationComplete: $isRegistrationComplete))
			}
		}
	}
}
