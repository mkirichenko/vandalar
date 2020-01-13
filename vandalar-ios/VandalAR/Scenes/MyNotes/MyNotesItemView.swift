import SwiftUI

struct MyNotesItemView: View {
	private let note: Note
	
	init(note: Note) {
		self.note = note
	}
	
    var body: some View {
		HStack {
			Text(note.content)
				.lineLimit(1)
			Spacer()
			Text("\(note.lat) \(note.lon)")
		}
    }
}

#if DEBUG
struct MyNotesItemView_Previews: PreviewProvider {
    static var previews: some View {
		let note = Note(
			id: 0,
			content: "I was here",
			lat: 54.843019,
			lon: 83.090502,
			height: 0,
			created: Date()
		)
		return MyNotesItemView(note: note)
    }
}
#endif
