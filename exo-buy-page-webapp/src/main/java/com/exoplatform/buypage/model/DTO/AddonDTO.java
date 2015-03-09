/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.buypage.model.DTO;


import java.math.BigDecimal;
import java.util.List;

public class AddonDTO extends DTO {

  public static final String PREFIX_ADDON = "EXO_PLT";
  public static final String PREFIX_SERVICE = "SV";
  public AddonDTO(){

  }
  private List<OptionDTO> optionDTOs;

  public AddonDTO(String id, String name, String description, BigDecimal price) {
    super(id,name,description);
    super.setPrice(price);
  }

  public List<OptionDTO> getOptionDTOs() {
    return optionDTOs;
  }

  public void setOptionDTOs(List<OptionDTO> optionDTOs) {
    this.optionDTOs = optionDTOs;
  }

  public Boolean isService() {
    if (this.getId().startsWith(PREFIX_SERVICE))
      return true;
    return false;
  }

}
