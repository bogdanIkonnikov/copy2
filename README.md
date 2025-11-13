Инструкция по работе с репозиторием
1) Скопируйте репозиторий выполнив в терминале
   ```git clone <URL_репозитория>```
2) Перейдите к ветке main
   ```git switch main```
3) Переключитесь на директорию backend
   ```cd backend```
4) Для запуска в Docker:
   Убедитесь, что на компьютере установлены Docker и docker-compose:
      проверить версию Docker:
      ```docker --version```
      проверить версию docker-compose:
      ```docker-compose --version```
      если они не утановлены, то установить с официальных сайтов
   Запустите проект:
   ```docker-compose up --build```
