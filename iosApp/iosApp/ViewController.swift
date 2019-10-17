import UIKit
import app

class ViewController: UIViewController, MainView {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let kodeinImpl = KodeinDI().getKodein()
        let presenter = MainViewPresenter(kodein: kodeinImpl, view: self);
        presenter.loadUsers()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBOutlet weak var label: UILabel!
    
    func showUsers(users: [User]) {
        self.label.text = users[0].first_name
    }
}
