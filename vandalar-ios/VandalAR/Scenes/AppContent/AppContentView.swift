import Foundation
import SwiftUI

struct AppContentView: View {
	typealias Context = VandalAPIServiceType
	@State private var isRegistrationComplete: Bool
	@State private var shouldNavigateToMyNotes = false
	@State private var shouldNavigateToAddNote = false
	private let context: Context
	
	init(isRegistrationComplete: Bool, context: Context, privateId: UUID?) {
		_isRegistrationComplete = State(initialValue: isRegistrationComplete)
		self.context = context
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
								destination: MyNotesView(viewModel: MyNotesViewModel(context: context)),
								isActive: self.$shouldNavigateToMyNotes
							) {
								EmptyView()
							}.hidden()
							
							NavigationLink(
								destination: AddNoteView(viewModel: AddNoteViewModel(context: context, showSelf: self.$shouldNavigateToAddNote)),
								isActive: self.$shouldNavigateToAddNote
							) {
								EmptyView()
							}.hidden()
							
							Button(action: { self.shouldNavigateToMyNotes = true }) {
								Image(systemName: "list.bullet")
							}
							Spacer()
							Button(action: { self.shouldNavigateToAddNote = true }) {
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
