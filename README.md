api_project_annuel


#User
- id
- email
- password
- firstname
- lastname
- profilePicture //todo pas encore mit
- userRoleGroupId manytomany

#follow
- id(car obliger)
- follower userid
- follow (l'id)

#UserRoleGroup
- id
- idGroup 
- idUser
- idRole

#Role (user dans un groupe)
- id
- nom

#group
- id
- nom

#Post
- id
- userId
- image / codeId / text / son / snippetId

#Like
- id(car obliger)
- userid
- postId


#Dislike 
- userId
- postId

#Comment
- id
- content (string)
- id post
- idUser

#code
- id
- id language
- content
- userId
- nb de vue

#language
- id
- nom


#Snippet
- id
- id language
- nom
- date creation
- userId
- content
