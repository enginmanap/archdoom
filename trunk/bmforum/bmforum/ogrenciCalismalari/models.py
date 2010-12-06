from django.db import models
from django.core.management.validation import max_length
from optparse import choices

academic_titles = (
                   "arastirma gorevlisi",
                   "ogretim gorevlisi",
                   "yardimci docent doktor",
                   "docent doktor",
                   "profesor doktor")

class Professor(models.Model):
    name = models.CharField(max_length=30,verbose_name="Ogretmen_ismi")
    academicTitle =  models.CharField(choices= academic_titles)


# Create your models here.
