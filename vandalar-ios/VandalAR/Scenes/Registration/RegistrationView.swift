import Foundation
import Combine
import SwiftUI

struct RegistrationView : View {
	@ObservedObject private var viewModel: RegistrationViewModel
	@State private var name: String = ""
	
	init(viewModel: RegistrationViewModel) {
		self.viewModel = viewModel
	}
	
    var body: some View {
		NavigationView {
			VStack {
				Form {
					Section(footer:
						Text("This is how you will be known to other vandals.")
					) {
						TextField("Enter your name", text: $name)
							.textContentType(.nickname)
					}
					
					Section {
						Button("Register", action: {
							self.viewModel.register(name: self.name)
						})
						.disabled(name.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty)
					}
				}
				.navigationBarTitle("Become a Vandal")
			}
		}
		.alert(isPresented: $viewModel.isShowingAlert) {
			Alert(title:
				Text(viewModel.alertMessage ?? "Unknown error")
			)
		}
    }
}

#if DEBUG
struct RegistrationView_Previews : PreviewProvider {
    static var previews: some View {
		let viewModel = RegistrationViewModel(context: VandalAPIServiceMock(), isRegistrationComplete: .constant(false))
		return RegistrationView(viewModel: viewModel)
    }
}
#endif
