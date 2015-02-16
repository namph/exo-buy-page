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


import java.util.List;

public class AddonDTO {
  private String id;
  private String name;
  private String descrtiption;
  private List<OptionDTO> optionDTOs;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescrtiption() {
    return descrtiption;
  }

  public void setDescrtiption(String descrtiption) {
    this.descrtiption = descrtiption;
  }

  public List<OptionDTO> getOptionDTOs() {
    return optionDTOs;
  }

  public void setOptionDTOs(List<OptionDTO> optionDTOs) {
    this.optionDTOs = optionDTOs;
  }
}
