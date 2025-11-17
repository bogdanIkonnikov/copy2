Инструкция по работе с репозиторием

Инициализация
1. Скопируйте репозиторий выполнив в терминале
	`git clone https://github.com/bogdanIkonnikov/copy2.git`
	`git submodule update --init --recursive`
2. Перейдите к ветке main `git switch git switch create-test`
3. Для запуска в Docker:
	1. Убедитесь, что на компьютере установлены Docker и docker-compose: 
		1. Проверить версию Docker: `docker --version` 
		2. Проверить версию docker-compose: `docker-compose --version`
		3. Если они не установлены, то установить с официальных сайтов
	2. Запустите проект: `docker-compose up --build`
 
Обновление
1. Выполните в терминале 
	`git pull`
	`git submodule update --init --remote`
