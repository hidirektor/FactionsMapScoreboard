package com.infumia.t3sl4.facmap.utils.freemaker.wrapper;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import java.util.UUID;

public class UUIDModel implements TemplateScalarModel, TemplateHashModel {
   private UUID uuid;

   public UUIDModel(UUID uuid) {
      this.uuid = uuid;
   }

   public TemplateModel get(String s) throws TemplateModelException {
      return new SimpleScalar(this.uuid.toString().replace("-", ""));
   }

   public boolean isEmpty() throws TemplateModelException {
      return false;
   }

   public String getAsString() throws TemplateModelException {
      return this.uuid.toString();
   }
}
