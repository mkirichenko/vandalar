//
//  ARKitView.swift
//  VandalAR
//
//  Created by Egor Topolnyak on 12.01.2020.
//  Copyright © 2020 NSU. All rights reserved.
//

import SwiftUI
import ARCL
import CoreLocation
import ARKit

struct ARKitView: UIViewRepresentable {
    var sceneLocationView: SceneLocationView = SceneLocationView()
    
    func makeUIView(context: Context) -> SceneLocationView {
        sceneLocationView.run()
        return sceneLocationView
    }

    func updateUIView(_ uiView: SceneLocationView, context: Context) {
        let note = Note(id: 0, content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", lat: 54.83884, lon: 83.10913, height: 182, created: Date())
       
        addNoteToARScene(note: note)
    }
    
    private func addNoteToARScene(note: Note) {
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
        annotationNode.scaleRelativeToDistance = true
        
        sceneLocationView.addLocationNodeWithConfirmedLocation(locationNode: annotationNode)
    }
}
