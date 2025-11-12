Инструкция по работе с репозиторием
1) Скопируйте репозиторий выполнив в терминале
   ```git clone <URL_репозитория>```
2) Перейдите к ветке main
   ```git switch main```
4) Переключитесь на директорию backend
   ```cd backend```
5.1) Для запуска в Docker:
   Убедитесь, что на компьютере установлены Docker и docker-compose:
      проверить версию Docker:
      ```docker --version```
      проверить версию docker-compose:
      ```docker-compose --version```
      если они не утановлены, то установить с официальных сайтов
   Запустите проект:
   ```docker-compose up --build```
5.2) Для локального запуска: !!!!!!!!!!!!!!!!!!ДОБИТЬ
   1) Убедитесь, что на компьютере установлены:
        JDK 17 или выше (проверьте командой java -version)
        Gradle (если Gradle wrapper отсутствует, проверьте командой gradle -v)
   2) Запустите проект с помощью Gradle wrapper:
        Для Linux/macOS:
        ```./gradlew bootRun```
        Для Windows:
       ```gradlew.bat bootRun```
