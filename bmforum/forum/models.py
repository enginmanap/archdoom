from django.db import models

# Create your models here.

class User(models.Model):




class Entry(models.Model):
	user = models.ForeignKey(User, related_name = "entry_author")
	topic = models.ForeignKey(Topic, related_name = "entry_author")
	text = models.TextField(verbose_name = "icerik")



class Topic(models.Model):



	
