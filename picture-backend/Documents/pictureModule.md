## demand 
1. admin functions
- picture's create and upload
  - allow upload local picture, input infomation include(name, tag, description, categorises)
  - system will analyze the infomation of picture, include **height and weight**, faciliating the search
- picture management(search and delete)
- picture modify(edit infomation)
2. user functions
- search and look the picture(main page)
  - user search pictuyre with keyword in the main page
  - allow filter picture infomation
- look pictures detail infomation
- download the picture
## document download method
1. object storage(bucket)->backend(storage)->frontend
2. bucket->backend(streaming)->frontend
3. (for the security purpose)bucket->frontend+temp auth key(bucket->backend(auth->temp key))
4. (least secure, cheapest)bucket->frontend(url)