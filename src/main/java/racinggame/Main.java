package racinggame;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Car> cars = InputView.inputCarNames().stream()
                .map(Car::new)
                .collect(Collectors.toList());
        int tryCount = InputView.inputTryCount();
        RacingGame game = new RacingGame(new RandomNumberGenerator());
        RacingGameResult result = game.run(cars, tryCount);
        OutputView.printResult(result);
    }

}
