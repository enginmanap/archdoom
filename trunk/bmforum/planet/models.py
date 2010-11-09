from django.db import models

class Planet(models.Model):
    blogAddress = models.URLField(verbose_name = "blog_adresi")
    isHidden = models.BooleanField(verbose_name = "silindi_mi?", default=False)
    user = models.ForeignKey(User, related_name = "blog_sahibi")
    
class BlogEntry(models.Model):
    blog = models.ForeignKey(Planet, related_name = "blog")
    title = models.CharField(verbose_name = "girdi_basligi")
    text = models.TextField(verbose_name = "girdi_icerigi")
    date = models.DateTimeField(verbose_name = "girdi_tarihi")