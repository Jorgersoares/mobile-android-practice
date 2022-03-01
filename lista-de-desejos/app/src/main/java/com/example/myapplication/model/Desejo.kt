import java.io.Serializable
class Desejo: Serializable {
    var descricao: String
    var valor: Float
    constructor(descricao: String, valor: Float){
        this.descricao = descricao
        this.valor = valor
    }
    override fun toString(): String {
        return "${this.descricao} - R$ ${this.valor}"
    }
}