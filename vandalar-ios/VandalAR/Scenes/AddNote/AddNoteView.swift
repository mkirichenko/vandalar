import SwiftUI

struct AddNoteView: View {
	@ObservedObject private var viewModel: AddNoteViewModel
	@State private var note: String = ""
	
	init(viewModel: AddNoteViewModel) {
		self.viewModel = viewModel
	}
	
    var body: some View {
		Form {
			Section(header: Text("")) {
				TextField("Write something here", text: $note)
			}
			Section {
				Button(action: { self.viewModel.add(self.note) }) {
					Text("Add note")
				}.disabled(note.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty)
			}
		}
    }
}

struct AddNoteView_Previews: PreviewProvider {
    static var previews: some View {
		let viewModel = AddNoteViewModel(context: VandalAPIServiceMock(), showSelf: .constant(true))
		return NavigationView {
			AddNoteView(viewModel: viewModel)
		}
    }
}
