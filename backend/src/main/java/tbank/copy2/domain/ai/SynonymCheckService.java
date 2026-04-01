package tbank.copy2.domain.ai;

public interface SynonymCheckService {
    boolean check(String correctAnswer, String currentAnswer);
}
