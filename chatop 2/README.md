# Projet 3 Chapot P3 

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.

## Front-end Preparation

1. Install Node.js: [Download Node.js](https://nodejs.org/en/download)

2. Install Angular CLI locally:

    ```sh
    npm install -g @angular/cli
    ```

3. Clone the repository:

    ```sh
    git clone https://github.com/OpenClassrooms-Student-Center/P3-Full-Stack-portail-locataire
    ```

4. Move to the project directory:

    ```sh
    cd P3-Full-Stack-portail-locataire
    ```

5. Install dependencies:

    ```sh
    npm install
    ```

6. Start the development server:

    ```sh
    npm run start
    ```

7. Open your browser and go to [http://localhost:4200/](http://localhost:4200/) to view the application.

## Back-end Preparation

1. Install SQL: [Download MySQL](https://dev.mysql.com/downloads/installer/)

2. Import the database found in `resources/sql/script.sql` of the Angular project.

3. Create environment variables:

   ### On Linux/Mac:

    ```sh
    export AWS_ACCESS_KEY_ID=your_access_key_id
    export AWS_SECRET_ACCESS_KEY=your_secret_access_key
    export AWS_BUCKETNAME=your_bucket_name
    export SPRING_DATASOURCE_PASSWORD=your_datasource_password
    export SPRING_DATASOURCE_URL=your_datasource_url
    export SPRING_DATASOURCE_USERNAME=your_datasource_username
    ```

   ### On Windows (PowerShell):

    ```sh
    setx AWS_ACCESS_KEY_ID "your_access_key_id"
    setx AWS_SECRET_ACCESS_KEY "your_secret_access_key"
    setx AWS_BUCKETNAME "your_bucket_name"
    setx SPRING_DATASOURCE_PASSWORD "your_datasource_password"
    setx SPRING_DATASOURCE_URL "your_datasource_url"
    setx SPRING_DATASOURCE_USERNAME "your_datasource_username"
    ```

4. Clone the repository:

    ```sh
    git clone https://github.com/zcbe/P3Chatop.git
    ```

5. Move to the project directory:

    ```sh
    cd P3Chatop
    ```

6. Start the project:

    ```sh
    mvn spring-boot:run
    ```

