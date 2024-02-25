Firstly, set OPENAI_API_KEY:
```
export OPENAI_API_KEY=sk-...
```

Let the GPT Engineer generates code:
```
cd gpt-engineer
docker-compose up
```

After generating code successfully you can see the files in your microservices folder.
And you can run them with:

```
cd {microservice}
mvn spring-boot:run
```

> Note: Before you run the application, Ensure to use JDK 17 and run the infra (Kafka):
```
sdk install java 17.0.4.1-tem
cd infra
docker-compose up
```

> Node: To totally regenerate the code, you have to delete the directory as well:
```
sudo rm -rf .gpteng
```

