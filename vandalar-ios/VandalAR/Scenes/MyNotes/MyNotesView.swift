import SwiftUI

struct MyNotesView: View {
	@ObservedObject private var viewModel: MyNotesViewModel
	
	init(viewModel: MyNotesViewModel) {
		self.viewModel = viewModel
	}
	
    var body: some View {
		Group {
			if viewModel.notes.isEmpty {
				Text("No notes yet")
			} else {
				List(viewModel.notes, id: \.id) { note in
					MyNotesItemView(note: note)
				}
			}
		}
		.navigationBarTitle("My notes")
		.onAppear {
			self.viewModel.fetchNotes()
		}
		.alert(isPresented: $viewModel.isShowingAlert) {
			Alert(title:
				Text(viewModel.alertMessage ?? "Unknown error")
			)
		}
    }
}

#if DEBUG
struct MyNotesView_Previews: PreviewProvider {
    static var previews: some View {
		let viewModel = MyNotesViewModel(context: VandalAPIServiceMock(), privateId: UUID())
		return MyNotesView(viewModel: viewModel)
    }
}
#endif
