import UIKit
import app

class ViewController: UIViewController {
    let api = ApplicationApi()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        label.text = Proxy().proxyHello()
        
        api.about { (description) in
            self.label.text = description
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
}
