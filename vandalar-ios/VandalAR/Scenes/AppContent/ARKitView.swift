import SwiftUI
import ARKit_CoreLocation
import CoreLocation
import ARKit

struct ARKitView: UIViewRepresentable {
    private let locationManager = CLLocationManager()
	@ObservedObject private var viewModel: ARKitViewModel
	
	init(viewModel: ARKitViewModel) {
		self.viewModel = viewModel
	}
    
    public func makeUIView(context: Context) -> SceneLocationView {
        let sceneLocationView: SceneLocationView = SceneLocationView()
        sceneLocationView.run()
        return sceneLocationView
    }

    public func updateUIView(_ uiView: SceneLocationView, context: Context) {
        uiView.removeAllNodes()
        
		if viewModel.notes.isEmpty {
			guard let location = locationManager.location else { return }
            let note = NoteWithAuthor(
				id: 0,
				content: "No notes added, sorry",
				userId: "595da550-37b7-11ea-aec2-2e728ce88125",
				userName: "Somebody",
				lat: location.coordinate.latitude,
				lon: location.coordinate.longitude,
				height: 200,
				created: Date()
			)
            
            addNote(note: note, to: uiView)
            return
        }
       
		for note in viewModel.notes {
            addNote(note: note, to: uiView)
        }
    }
    
    private func addNote(note: NoteWithAuthor, to scene: SceneLocationView) {
        let coordinate = CLLocationCoordinate2D(latitude: note.lat, longitude: note.lon)
        let location = CLLocation(coordinate: coordinate, altitude: note.height)
        
        let view = UIView()
        
        let label:UILabel = UILabel(frame: CGRect(x: 0, y: 0, width: 100, height: CGFloat.greatestFiniteMagnitude))
        label.numberOfLines = 0
        label.lineBreakMode = NSLineBreakMode.byWordWrapping
        label.text = note.content
        label.adjustsFontSizeToFitWidth = true
        label.textColor = .black

        label.sizeToFit()
       
        view.frame = CGRect(x: 0, y: 0, width: 100, height: 100)
        view.addSubview(label)
        
        view.backgroundColor = .white

        let annotationNode = LocationAnnotationNode(location: location, view: view)
        annotationNode.scaleRelativeToDistance = false
        
        scene.addLocationNodeWithConfirmedLocation(locationNode: annotationNode)
    }
}
