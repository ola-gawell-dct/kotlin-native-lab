package sample

class SamplePresenter {

    var view: SampleView? = null

    //var label: KMutableLiveData<String> = KMutableLiveData()

    fun getData() {
        GetHttpDataUseCase().execute({
            log("Data from http request: $it")
            view?.setLabel(it)
        })
    }
}

interface SampleView {
    fun setLabel(text: String)
}