import SwiftUI

struct NoteDetail: View {
	private let note: Note
	
	init(note: Note) {
		self.note = note
	}
	
    var body: some View {
		ScrollView {
			VStack(alignment: .leading, spacing: .some(32)) {
				Text(note.content)
					.font(.headline)
				HStack {
					Text("\(note.lat) \(note.lon)")
					Spacer()
					Text(DateFormatter.localizedString(from: note.created, dateStyle: .short, timeStyle: .short))
				}
			}.padding()
		}
		.navigationBarItems(trailing: Image(systemName: "trash").foregroundColor(.red))
	}
}

struct NoteDetail_Previews: PreviewProvider {
    static var previews: some View {
		let note = Note(id: 0, content: "I was here", lat: 50.348, lon: 34.3333, height: 10, created: Date())
		return NavigationView {
			NoteDetail(note: note)
		}
    }
}
