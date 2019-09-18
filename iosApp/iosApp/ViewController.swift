import UIKit
import app

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let ktorApi = KtorAPI()
        let useCase = GetUsersUseCase(api: ktorApi);
        useCase.execute(onComplete: { (response) in
            self.label.text = response.data[0].first_name
            print(response)
        }, onError: { (e) in
            print(e)
        }) { (e) in
            print(e)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
}
