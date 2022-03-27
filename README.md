api_project_annuel


#User
- id
- firstname
- lastname
- email
- password
- profilePicture

#follow
- user qui follow (l'id)
- user se fait follow (l'id)

#UserRoleGroup
- id
- idUser
- idGroup
- idRole

#Role (user dans un groupe)
- id
- nom

#group
- id
- nom
- idUserGroupRole


#Post
- id
- userId
- contentId 
- like
- dislike
	
#Content
- id
- image / code / text / data / son / code / snipet 

#Comment
- id
- contentId
- id post
- idUser

#code
- id
- id language
- content
- userId

#language
- id
- nom


#snipet
- id
- id language
- nom
- date creation
- userId
- content
