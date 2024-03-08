import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TicketMachine {

    protected int valor;
    protected int saldo;
    protected int[] papelMoeda = {100, 50, 20, 10, 5, 2}; // Ordenando em ordem decrescente

    public TicketMachine(int valor) {
        this.valor = valor;
        this.saldo = 0;
    }

    public void inserir(int quantia) throws PapelMoedaInvalidaException {
        if (quantia <= 0) {
            throw new PapelMoedaInvalidaException("Quantia inválida");
        }
        boolean achou = false;
        for (int i = 0; i < papelMoeda.length && !achou; i++) {
            if (papelMoeda[i] == quantia) {
                achou = true;
            }
        }
        if (!achou) {
            throw new PapelMoedaInvalidaException("Papel moeda inválido: " + quantia);
        }
        this.saldo += quantia;
    }

    public int getSaldo() {
        return saldo;
    }

    public Iterator<Integer> getTroco() {
        List<Integer> troco = new ArrayList<>();
        int valorTroco = saldo - valor;
        for (int i = 0; i < papelMoeda.length; i++) {
            while (valorTroco >= papelMoeda[i]) {
                troco.add(papelMoeda[i]);
                valorTroco -= papelMoeda[i];
            }
        }
        return troco.iterator();
    }

    public String imprimir() throws SaldoInsuficienteException {
        if (saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para imprimir");
        }
        int troco = saldo - valor;
        this.saldo -= valor; // Deduz o valor do ticket do saldo
        StringBuilder result = new StringBuilder("*****************\n");
        result.append("*** R$ ").append(saldo).append(",00 ****\n");
        result.append("*****************\n");
        if (troco > 0) {
            result.append("Troco:\n");
            Iterator<Integer> iterator = getTroco();
            while (iterator.hasNext()) {
                result.append(iterator.next()).append("\n");
            }
        }
        return result.toString();
    }
}
