import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel {
    var counter = 0
    fun incrementCounter(){
        counter++
    }
}