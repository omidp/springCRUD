# SpringCRUD
Pimefaces + Spring CRUD 

This repo is deprecated, use [https://github.com/jedlab/crud](https://github.com/jedlab/crud)

## Clone project

```
git clone https://github.com/omidp/springCRUD.git
```

## Build 

```
mvn clean install
```

## Add dependency to your project

```
<dependency>
			<groupId>com.omidbiz</groupId>
			<artifactId>springCRUD</artifactId>
			<version>0.0.1</version>
</dependency>
```

## Create Entity Model

```
public class AgentEntity extends AbstractEntity
```

## Create DAO

```
public interface AgentDAO extends AbstractDAO<AgentEntity>
```



## Create Service

```
public class AgentService extends AbstractService<AgentEntity>
{

	@Autowired
	private AgentDAO dao;

	public AbstractDAO<AgentEntity> getDao()
	{
		return dao;
	}

}

```

