package com.shinkwang.hackingspringboot.reactive;

import reactor.core.publisher.Flux;

public class DishMetaphor {

    static class KitchenService {

        Flux<Dish> getDishes() {
            // 요리를 담당하는 ChefService를 모델링해서 요리를 위임할 수도 있지만,
            // 단순하게 설명하기 위해 하드 코딩으로 대체

            return Flux.just(
                    new Dish("Sesame chicken"),
                    new Dish("Lo mein noodles, plain"),
                    new Dish("Sweet & sour beef"));
        }
    }

    class SimpleServer {

        private final KitchenService kitchen;

        SimpleServer(KitchenService kitchen) {
            this.kitchen = kitchen;
        }

        Flux<Dish> doingMyJob() {
            return this.kitchen.getDishes()
                    .map(dish -> Dish.deliver(dish));
        }
    }

    static class PoliteServer {

        private final KitchenService kitchen;

        PoliteServer(KitchenService kitchen) {
            this.kitchen = kitchen;
        }

        Flux<Dish> doingMyJob() {
            return this.kitchen.getDishes()
                    .doOnNext(dish -> System.out.println("Thank you for " + dish + "!"))
                    .doOnError(error -> System.out.println("So sorry about " + error.getMessage()))
                    .doOnComplete(() -> System.out.println("Thank for all your hard work!"))
                    .map(Dish::deliver);
        }
    }

    static class Dish {
        private String description;
        private boolean delivered = false;

        public static Dish deliver(Dish dish) {
            Dish deliveredDish = new Dish(dish.description);
            deliveredDish.delivered = true;
            return deliveredDish;
        }

        Dish(String description) {
            this.description = description;
        }

        public boolean isDelivered() {
            return delivered;
        }

        @Override
        public String toString() {
            return "Dish{" + "description='" + description + '\'' + ", delivered=" + delivered + '}';
        }
    }

    static class PoliteRestaurant {

        public static void main(String... args) {
            PoliteServer server= new PoliteServer(new KitchenService());

            server.doingMyJob().subscribe(
                    dish -> System.out.println("Consuming " + dish),
                    throwable -> System.err.println(throwable));
        }
    }
}
