//Observible interface so scripts can reference this and implement
public interface Observible {
    void Subscribe(Observer observer);
    void UnSubscribe(Observer observer);
    void UpdateObserversItems();
}