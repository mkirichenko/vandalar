import Foundation
import SwiftUI

struct AppContentView: View {
	typealias Context = VandalAPIServiceType
	@State private var isRegistrationComplete: Bool
	@State private var shouldNavigateToMyNotes: Bool = false
	private let context: Context
	private let privateId: UUID?
	
	init(isRegistrationComplete: Bool, context: Context, privateId: UUID?) {
		_isRegistrationComplete = State(initialValue: isRegistrationComplete)
		self.context = context
		self.privateId = privateId
	}
	
	var body: some View {
		Group {
			if isRegistrationComplete {
				NavigationView {
					VStack {
						ARKitView().frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
							.edgesIgnoringSafeArea(.all)
						HStack {
							NavigationLink(
								destination: MyNotesView(viewModel: MyNotesViewModel(context: context, privateId: privateId!)),
								isActive: self.$shouldNavigateToMyNotes
							) {
								EmptyView()
							}.hidden()
							
							Button(action: { self.shouldNavigateToMyNotes = true }) {
								Image(systemName: "list.bullet")
							}
							Spacer()
							Button(action: {}) {
								Image(systemName: "plus")
							}
						}.padding()
					}
				}
			} else {
				RegistrationView(viewModel: RegistrationViewModel(context: context, isRegistrationComplete: $isRegistrationComplete))
			}
		}
	}
}
