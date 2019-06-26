import UIKit
import app

class ViewController: UIViewController {
    let api = ApplicationApi()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        label.text = Proxy().proxyHello()
        
        class SwiftView: SampleView {
            
            let mainLabel: UILabel
            
            init(label: UILabel) {
                self.mainLabel = label
            }
            
            func setLabel(text: String) {
                self.mainLabel.text = text
            }
        }
        
        let vm = SamplePresenter()
        vm.view = SwiftView(label: self.label)
        vm.getData()
    
        
        /*api.about { (description) in
            self.label.text = description
        }*/
        
        label.text = "Test"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
}
